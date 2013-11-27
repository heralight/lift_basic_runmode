import sbt._
import Keys._
import com.untyped.sbtrunmode.Plugin._
import sbt.Project.Initialize
import com.earldouglas.xsbtwebplugin.{PluginKeys=>WebKeys}

object RootProject extends Build {


  def rootSettings = Project.defaultSettings  ++ runModeSettings ++ Seq(
    cpDevBootstrapTask.in(Development) <<=  cpDevBootstrapAction,
    cpProdBootstrapTask.in(Production) <<=  cpProdBootstrapAction,
    makeDevBootstrapTask.in(Development) <<=  makeDevBootstrapAction,

    WebKeys.webappResources.in(Development) <<= WebKeys.webappResources.in(Compile),
    WebKeys.webappResources.in(Production) <<= WebKeys.webappResources.in(Compile)
  //,
     // compile in Development <<= compile in Development dependsOn (cpDevBootstrapTask in  Development),
       // LessKeys.less.in(Development)
  )

  lazy val rootProject = Project(id = "root",
    base = file("."),
    settings = rootSettings)

  val cpDevBootstrapTask = TaskKey[Unit]("cp-dev-bootstrap")

  val cpDevBootstrapAction = (streams) map {
    s => {
      List("sh", "-c", "(mkdir -p target/scala-2.10/resource_managed/main/; cp -r modules/bootstrap/bootstrap/* target/scala-2.10/resource_managed/main/)") ! ;
      s.log.info("cp-dev-bootstrap")
    }
  }

  val makeDevBootstrapTask = TaskKey[Unit]("make-dev-bootstrap")

  val makeDevBootstrapAction = (streams) map {
    s => {
    //  List("sh", "-c", "(cd modules/bootstrap/; make clean; make bootstrap;)") ! ;
      s.log.info("make-dev-bootstrap")
    }
  }

  val cpProdBootstrapTask = TaskKey[Unit]("cp-prod-bootstrap")

  val cpProdBootstrapAction = (streams) map {
    s => {
      List("sh", "-c", "(mkdir -p target/scala-2.10/resource_managed/main/; cp -r modules/bootstrap/bootstrap/img target/scala-2.10/resource_managed/main/)") ! ;
      s.log.info("cp-prod-bootstrap")
    }
  }

}
