package org.openmole.core.workflow.tools

import org.openmole.core.exception.UserBadDataError
import org.openmole.core.expansion.{ FromContext, ToFromContext }

object OptionalArgument {
  //implicit def valueToOptionalOfForPuzzle[T](v: T)(implicit toPuzzle: ToPuzzle[T]) = OptionalArgument(Some(toPuzzle.toPuzzle(v)))

  implicit def valueToOptionalOfForContext[T](v: T)(implicit toFromContext: ToFromContext[T, T]) = OptionalArgument(Some(FromContext.contextConverter(v)))
  implicit def valueToOptionalArgument[T](v: T) = OptionalArgument(Some(v))
  implicit def noneToOptionalArgument[T](n: None.type) = OptionalArgument[T](n)

  def apply[T](t: T): OptionalArgument[T] = OptionalArgument(Some(t))
}

case class OptionalArgument[T](option: Option[T] = None) {
  def mustBeDefined(name: String) = option.getOrElse(throw new UserBadDataError(s"Parameter $name has not been set."))
}
