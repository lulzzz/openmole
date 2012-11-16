/*
 * Copyright (C) 2011 Mathieu Leclaire
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openmole.ide.plugin.domain.modifier

import java.math.BigDecimal
import java.math.BigInteger
import org.openmole.core.model.domain.{ Discrete, Domain }
import org.openmole.ide.core.model.dataproxy.IPrototypeDataProxyUI
import org.openmole.ide.core.model.data.{ IFactorDataUI, IDomainDataUI }
import org.openmole.misc.exception.UserBadDataError
import org.openmole.plugin.domain.modifier.TakeDomain
import org.openmole.ide.core.implementation.dataproxy.PrototypeDataProxyUI
import org.openmole.ide.core.implementation.prototype.GenericPrototypeDataUI
import org.openmole.ide.core.implementation.dialog.StatusBar

class TakeDomainDataUI(val size: String = "1",
                       val previousDomain: Option[IDomainDataUI] = None)
    extends ModifierDomainDataUI {

  val domainType = previousDomain match {
    case Some(dt: IDomainDataUI) ⇒ dt.domainType
    case _ ⇒ manifest[Double]
  }

  val name = "Take"

  def preview = "Take (" + size + ")"

  override def coreObject = previousDomain match {
    case Some(pD: IDomainDataUI) ⇒ pD.coreObject match {
      case d: DOMAINTYPE ⇒ new TakeDomain(d, size.toInt)
      case _ ⇒ throw new UserBadDataError("A Discrete Domain is required as input of a Take Domain.")
    }
    case _ ⇒ throw new UserBadDataError("A Discrete Domain is required as input of a Take Domain.")
  }

  def buildPanelUI = new TakeDomainPanelUI(this)

  def coreClass = classOf[TakeDomainDataUI]

  override def toString = "Take"
}
