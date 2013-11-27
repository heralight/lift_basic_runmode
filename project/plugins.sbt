resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += Resolver.url("untyped", url("http://ivy.untyped.com"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.4.2")

//Uncoment this line to enable the sbt idea plugin
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0-SNAPSHOT")

//Uncoment this line to enable the sbt eclipse plugin
//addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.0")

addSbtPlugin("com.untyped" % "sbt-runmode" % "0.6")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.2.0")
