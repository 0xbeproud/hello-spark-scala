lazy val root = (project in file("."))
  .settings(
    name := "spark-scala",
    libraryDependencies ++= Dependencies.sparkProvided,
    libraryDependencies ++= Dependencies.jackson,
    libraryDependencies ++= Dependencies.test,

    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-streaming-kafka-0-10" % Versions.spark,
      "com.typesafe" % "config" % Versions.typeSafeConfig,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
      "com.amazonaws" % "aws-java-sdk-s3" % Versions.awsSDK,
      "com.amazonaws" % "aws-java-sdk-sqs" % Versions.awsSDK,
      "mysql" % "mysql-connector-java" % Versions.mysql,
      "com.zaxxer" % "HikariCP" % Versions.hikari,
      "com.github.ben-manes.caffeine" % "caffeine" % Versions.caffeine,
      "ch.qos.logback" % "logback-classic" % "1.2.10",
      "org.reactivestreams" % "reactive-streams" % "1.0.3",
      "org.redisson" % "redisson" % Versions.redisson,
      "com.hubspot.jinjava" % "jinjava" % Versions.jinjava,
      "com.lihaoyi" %% "requests" % Versions.requests,
      "com.github.pureconfig" %% "pureconfig" % "0.17.2"
    ),


  )

