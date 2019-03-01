

/*
 * Copyright (C) 2012 Romain Reuillon
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

package org.openmole.plugin.tool.pattern

import org.openmole.core.context.Val
import org.openmole.core.dsl._
import org.openmole.core.workflow.sampling.ExplicitSampling
import org.openmole.core.workflow.task.FromContextTask
import org.scalatest._

class PatternSpec extends FlatSpec with Matchers {

  import org.openmole.core.workflow.tools.Stubs._

  "Master slave" should "execute the slave and end on condition" in {
    @volatile var slaveExecuted = 0

    val i = Val[Int]
    val state = Val[Int]
    val sampling = ExplorationTask(ExplicitSampling(i, List(1, 2, 3)))

    val master = FromContextTask("master") { p ⇒
      import p._
      context + (state -> (context(state) + 1)) + (i.array -> Array(context(state)))
    } set ((inputs, outputs) += state, state := 0, exploredOutputs += i.array)

    val slave = FromContextTask("slave") { p ⇒
      import p._
      slaveExecuted += 1
      context
    } set (inputs += i)

    val ex = MasterSlave(sampling, master, slave, Seq(state)) >| EmptyTask() when "state > 10"

    ex.run
    slaveExecuted should be >= 10
  }

  "Skip" should "execute not execute the task" in {
    @volatile var testExecuted = false
    @volatile var lastExecuted = false

    val test = FromContextTask("test") { p ⇒
      import p._
      testExecuted = true
      context
    }

    val last = FromContextTask("last") { p ⇒
      import p._
      lastExecuted = true
      context
    }

    val mole = EmptyTask() -- Skip(test, true) -- last
    mole run

    testExecuted should equal(false)
    lastExecuted should equal(true)
  }

  "Switch" should "execute all branches where condition it true" in {
    @volatile var testExecuted = 0
    @volatile var lastExecuted = 0

    val test = FromContextTask("test") { p ⇒
      import p._
      testExecuted += 1
      context
    }

    val last = FromContextTask("last") { p ⇒
      import p._
      lastExecuted += 1
      context
    }

    Switch(
      Case(true, test),
      Case(true, test),
      Case(false, test)
    ) -- last run

    testExecuted should equal(2)
    lastExecuted should equal(2)
  }

  "While" should "execute the task multiple times" in {
    @volatile var testExecuted = 0

    val i = Val[Int]

    val test = FromContextTask("test") { p ⇒
      import p._
      testExecuted += 1
      context + (i -> (context(i) + 1))
    } set ((inputs, outputs) += i, i := 0)

    While(test, "i < 10") run ()

    testExecuted should equal(10)
  }

  "While" should "execute the task multiple times" in {
    @volatile var testExecuted = 0

    val i = Val[Int]

    val test = FromContextTask("test") { p ⇒
      import p._
      testExecuted += 1
      context + (i -> (context(i) + 1))
    } set ((inputs, outputs) += i, i := 0)

    While(test, "i < 10") run ()

    testExecuted should equal(10)
  }

}