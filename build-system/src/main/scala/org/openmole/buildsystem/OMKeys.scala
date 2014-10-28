package org.openmole.buildsystem

import sbt._
import scala.util.matching.Regex

/**
 * Created with IntelliJ IDEA.
 * User: luft
 * Date: 6/5/13
 * Time: 3:43 PM
 */
object OMKeys {

  val bundleType = SettingKey[Set[String]]("bundle-type") //Default setting for osgiprojects is default.

  val bundleProj = SettingKey[Boolean]("bundle-proj")

  val openMoleStandardVer = SettingKey[String]("openmole-version")

  val eclipseBuddyPolicy = SettingKey[Option[String]]("OSGi.eclipseBuddyPolicy", "The eclipse buddy policy thing.")

  val outDir = SettingKey[String]("outDir", "A setting to control where copyDepTask outputs it's dependencies")

  val install = TaskKey[Unit]("install", "Builds bundles and adds them to the local repo")

  val installRemote = TaskKey[Unit]("install-remote", "Builds bundles and adds them to the openmole nexus server")

  val assemblyPath = SettingKey[File]("assembly-path", "The path to the project's assembly folder")

  val assemble = TaskKey[File]("assemble", "The path with assembled project")

  //val gc = TaskKey[Unit]("gc", "Force SBT to take out the trash")

  val osgiVersion = SettingKey[String]("osgi-version")

  val osgiSingleton = SettingKey[Boolean]("osgi-singleton")

  // val Assemble = Tags.Tag("Assemble")

  val setExecutable = SettingKey[Seq[String]]("setExecutable", "Sets the path relative to the assemble folder executable")

  // val downloadUrls = TaskKey[Seq[File]]("download-urls")

  val downloads = SettingKey[Seq[(URL, String)]]("downloads", "A project setting that describes a urls to download")

  val resourcesAssemble = TaskKey[Seq[(File, String)]]("resourcesAssemble", "A set of (in,out) tuples that specify where to find the resource (in) and what sub-path of assembly to put it in (out)")

  val resourceOutDir = SettingKey[String]("resource-out-dir")

  val ignoreTransitive = SettingKey[Boolean]("ignoreTransitive")

  val dependencyFilter = SettingKey[ModuleID ⇒ Boolean]("dependency-filter", "Tells copyDependencies to ignore certain dependencies.")

  val dependencyNameMap = SettingKey[Map[Regex, String ⇒ String]]("dependency-map", "A map that is run against dependencies to be copied.")

  val scalatestVersion = SettingKey[String]("scalatest-version", "Version of scalatest.")

  val junitVersion = SettingKey[String]("junit-version", "Version of junit.")

  object Tar {
    val tar = TaskKey[File]("tar", "Tar file produced by the assembly project")
    val innerFolder = SettingKey[String]("tar-inner-folder", "All files in tar will be put under this folder")
    val name = SettingKey[String]("tar-name")

  }

}
