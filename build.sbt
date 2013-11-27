name := "Lift 2.5 starter template"

version := "0.0.1"

organization := "net.liftweb"

scalaVersion := "2.10.2"

javacOptions := Seq("-g", "-source", "1.7", "-target", "1.7", "-encoding", "utf8")


scalacOptions := Seq("-unchecked", "-optimise", "-feature",
  "-deprecation", "-Xcheckinit", "-encoding", "utf8",
  "-language:reflectiveCalls",
  "-language:implicitConversions",
  "-language:postfixOps")

resolvers ++= Seq( "fr" at "http://maven.antelink.com/content/repositories/central/",
					"uk" at "http://uk.maven.org/maven2"//,
					//"snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
					//"releases"        at "http://oss.sonatype.org/content/repositories/releases"
                )

ideaExcludeFolders += "logs"

ideaExcludeFolders += "project/project"

ideaExcludeFolders += "project/boot"

ideaExcludeFolders += "project/project/project/target"

ideaExcludeFolders += "project/project/target"

ideaExcludeFolders += "project/target"

ideaExcludeFolders += ".idea"

ideaExcludeFolders += ".idea_modules"

seq(webSettings :_*)

{
  object V {
    val lift = "2.5.1"
    val liftmodule = "2.5"
    //val Akka = "2.2.1"
    val databinder =  "0.11.0"
    val scalaz = "6.0.4"
    val spec2 = "2.2.3"
    val rogue = "2.2.0"
  }
  libraryDependencies ++= {
    Seq(
      "net.liftweb"       %% "lift-webkit"        % V.lift        % "compile",
      "net.liftweb"       %% "lift-mapper"        % V.lift        % "compile",
      //"net.liftmodules"   %% "lift-jquery-module" % (liftVersion + "-1.0"),
      "org.eclipse.jetty"       %  "jetty-webapp" % "9.0.5.v20130815"  % "container,test",
      "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
      "ch.qos.logback"    % "logback-classic"     % "1.0.9",
      "org.specs2"        %% "specs2"             %  V.spec2          % "test",
      "com.h2database"    % "h2"                  % "1.3.167"
    )
  }
}

///////////////// sbt-plugins
// .p. == production
// .d. == development
// .a. == add (a.p.xxx, a.d.xxx, a.xxx)
seq(runModeSettings : _*)

// add managed resources, where less and js closure publish to, to the webapp
(webappResources in Compile) <+= (resourceManaged in Compile)

///////////////// sbt-plugins Dev

//(webappResources in (Development)) <+= (resourceManaged in Development)

(compile in Development) <<= compile in Development dependsOn (LessKeys.less in Development)

(excludeFilter in (Development, LessKeys.less)) := ("*.p.less" )

(includeFilter in (Development, LessKeys.less)) := ("*.a.less" || "*.a.d.less")

(resourceManaged in (Development, LessKeys.less)) <<= (resourceManaged in Compile)(_ / "css" )

RunModeKeys.jettyVersion.in(Development) := JettyVersion.Jetty7Plus

LessKeys.less.in(Development) <<=  LessKeys.less.in(Development) dependsOn (cpDevBootstrapTask in  Development)

//LessKeys.useCommandLine in (Development, LessKeys.less) := true

/// JS
(compile in Development) <<= compile in Development dependsOn (JsKeys.js in Development)

(resourceManaged in (Development, JsKeys.js)) <<= (resourceManaged in Compile)(_ / "js" )

(excludeFilter in (Development,  JsKeys.js)) := ("*.p.*" : FileFilter)

(includeFilter in (Development,  JsKeys.js)) := ("*.a.js*" || "*.a.d.js*")

///////////////// sbt-plugins Dev End
///////////////// sbt-plugins Prod

RunModeKeys.jettyVersion.in(Production) := JettyVersion.Jetty7Plus

(compile in Production) <<= compile in Production dependsOn (LessKeys.less in Production)

(excludeFilter in (Production, LessKeys.less)) := ("*.d.*" : FileFilter)

(includeFilter in (Production, LessKeys.less)) := ("*.a.less" || "*.a.p.less")

(resourceManaged in (Production, LessKeys.less)) <<= (resourceManaged in Compile)(_ / "css" )

LessKeys.less.in(Production) <<=  LessKeys.less.in(Production) dependsOn (cpProdBootstrapTask in  Production)

//LessKeys.useCommandLine in (Production, LessKeys.less) := true

/// JS
(compile in Production) <<= compile in Production dependsOn (JsKeys.js in Production)

(resourceManaged in (Production, JsKeys.js)) <<= (resourceManaged in Compile)(_ / "js" )

(excludeFilter in (Production, JsKeys.js)) := ("*.d.*" : FileFilter)

(includeFilter in (Production,  JsKeys.js)) := ("*.a.js*" || "*.a.p.js*")

///////////////// sbt-plugins Prod End
