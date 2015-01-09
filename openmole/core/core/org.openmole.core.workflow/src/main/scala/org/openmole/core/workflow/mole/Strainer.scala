/*
 * Copyright (C) 09/01/13 Romain Reuillon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openmole.core.workflow.mole

import org.openmole.core.workflow.data.Data
import org.openmole.core.workflow.transition.Slot
import org.openmole.core.workflow.validation.TypeUtil

object Strainer {

  def isStrainer(c: Capsule) =
    c match {
      case _: StrainerCapsule ⇒ true
      case _                  ⇒ false
    }

  def reachNoStrainer(mole: Mole)(slot: Slot, seen: Set[Slot] = Set.empty): Boolean = {
    if (slot.capsule == mole.root) true
    else if (seen.contains(slot)) false
    else {
      val capsules = mole.inputTransitions(slot).map { _.start } ++ mole.inputDataChannels(slot).map { _.start }
      val noStrainer =
        for (c ← capsules; if (isStrainer(c)); s ← mole.slots(c)) yield reachNoStrainer(mole)(s, seen + slot)
      noStrainer.foldLeft(true)(_ & _)
    }
  }
}

import org.openmole.core.workflow.mole.Strainer._

trait Strainer extends Capsule {

  def received(mole: Mole, sources: Sources, hooks: Hooks) =
    if (this == mole.root) Iterable.empty
    else {
      val slots = mole.slots(this)
      val noStrainer = slots.filter(s ⇒ reachNoStrainer(mole)(s))
      TypeUtil.intersect(noStrainer.map { TypeUtil.receivedTypes(mole, sources, hooks) }).map(Data(_))
    }
}
