name := "Lift 2.5 starter template"

version := "0.0.1"

organization := "net.liftweb"

scalaVersion := "2.9.2"

resolvers ++= Seq( "fr" at "http://maven.antelink.com/content/repositories/central/",
					"uk" at "http://uk.maven.org/maven2"//,
					//"snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
					//"releases"        at "http://oss.sonatype.org/content/repositories/releases"
                )

seq(com.github.siasia.WebPlugin.webSettings :_*)

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.5-M3"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
    //"net.liftmodules"   %% "lift-jquery-module" % (liftVersion + "-1.0"),
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.7.v20120910"  % "container,test",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.0.6",
    "org.specs2"        %% "specs2"             % "1.12.1"           % "test",
    "com.h2database"    % "h2"                  % "1.3.167"
  )
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
