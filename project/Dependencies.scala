import sbt.Keys.libraryDependencies
import sbt._

object Dependencies {

  val spark = Seq(
    "org.apache.spark" %% "spark-yarn"      % Versions.spark,
    "org.apache.spark" %% "spark-core"      % Versions.spark,
    "org.apache.spark" %% "spark-sql"       % Versions.spark,
    "org.apache.spark" %% "spark-mllib"     % Versions.spark,
    "org.apache.spark" %% "spark-streaming" % Versions.spark,
    "org.apache.spark" %% "spark-hive"      % Versions.spark
  )

  val sparkProvided = Seq(
    "org.apache.spark" %% "spark-yarn"      % Versions.spark % Provided,
    "org.apache.spark" %% "spark-core"      % Versions.spark % Provided,
    "org.apache.spark" %% "spark-sql"       % Versions.spark % Provided,
    "org.apache.spark" %% "spark-mllib"     % Versions.spark % Provided,
    "org.apache.spark" %% "spark-streaming" % Versions.spark % Provided,
    "org.apache.spark" %% "spark-hive"      % Versions.spark % Provided
  )

  val jackson = Seq(
    "com.fasterxml.jackson.core"   % "jackson-core"          % "2.10.2",
    "com.fasterxml.jackson.core"   % "jackson-databind"      % "2.10.2",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.0"
  )

  val test = Seq(
    "com.h2database" % "h2" % "2.1.214" % Test
  )
}
