package root.gui

import org.openmole.buildsystem.OMKeys._

import sbt._
import root._
import root.Libraries._
import sbt.Keys._
import com.typesafe.sbt.osgi.OsgiKeys._
import root.gui.plugin.Task
import ThirdParties._

object Server extends GuiDefaults {
  override val dir = super.dir / "server"

  lazy val core = OsgiProject("org.openmole.gui.server.core") settings
    (libraryDependencies ++= Seq(autowire, upickle, scalaTags, jetty, logback, scalatra, txtmark)) dependsOn
    (
      Shared.shared,
      Ext.dataui,
      Ext.data,
      Core.workflow,
      Core.buildinfo,
      openmoleFile,
      openmoleTar,
      openmoleCollection,
      root.Runtime.console,
      Misc.utils,
      root.ThirdParties.openmoleStream,
      openmoleCrypto
    )

  lazy val state = OsgiProject("org.openmole.gui.server.state") settings
    (libraryDependencies ++= Seq(slick)) dependsOn
    (Ext.data, Core.workflow, Core.workspace)
}
