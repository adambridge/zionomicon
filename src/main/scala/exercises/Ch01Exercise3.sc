import zio.ZIO


// 3. Using the flatMap method of ZIO effects, together with the readFileZio and
// writeFileZiofunctions that you wrote, implement a ZIO version of the function
// copyFile.

def copyFile(source: String, dest: String): Unit = {
  val contents = readFile(source)
  writeFile(dest, contents)
}

def copyFileZio(source: String, dest: String) =
  readFileZio(source)
    .flatMap(lines =>
      writeFileZio(dest, lines)
    )



def readFile(file: String): String = {
  val source = scala.io.Source.fromFile(file)

  try source.getLines().mkString
  finally source.close()
}

def readFileZio(file: String) = ZIO.attempt(readFile(file))

def writeFile(file: String, text: String): Unit = {
  import java.io._
  val pw = new PrintWriter(new File(file))
  try pw.write(text)
  finally pw.close
}

def writeFileZio(file: String, text: String) = ZIO.attempt(writeFile(file, text))

