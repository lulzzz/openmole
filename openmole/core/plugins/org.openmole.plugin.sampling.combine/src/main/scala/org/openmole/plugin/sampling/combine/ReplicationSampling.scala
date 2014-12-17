/*
 * Copyright (C) 2011 Romain Reuillon
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

package org.openmole.plugin.sampling.combine

import org.openmole.core.implementation.tools.FromContext
import org.openmole.core.model.data._
import org.openmole.core.model.domain._
import org.openmole.core.model.sampling._
import org.openmole.plugin.domain.modifier._

import scala.util.Random

object ReplicationSampling {

  def apply[T](sampling: Sampling, seeder: Factor[T, Domain[T] with Discrete[T]], replications: FromContext[Int]): ReplicationSampling[T] =
    apply(sampling, Factor(seeder.prototype, seeder.domain.take(replications)))

  def apply[T](sampling: Sampling, seeder: Factor[T, Domain[T] with Discrete[T] with Finite[T]]): ReplicationSampling[T] =
    new ReplicationSampling(sampling, seeder)

}

sealed class ReplicationSampling[T](sampling: Sampling, seeder: Factor[T, Domain[T] with Discrete[T] with Finite[T]]) extends Sampling {

  override def inputs = sampling.inputs ++ seeder.inputs
  override def prototypes = seeder.prototype :: sampling.prototypes.toList

  override def build(context: Context)(implicit rng: Random): Iterator[Iterable[Variable[_]]] =
    CompleteSampling(sampling, seeder).build(context)

}
