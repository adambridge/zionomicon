import zio.ZIO


// 2. Implement a ZIO version of the function writeFileby using the ZIO.attempt
// constructor.

def writeFile(file: String, text: String): Unit = {
  import java.io._
  val pw = new PrintWriter(new File(file))
  try pw.write(text)
  finally pw.close
}

def writeFileZio(file: String, text: String) = ZIO.attempt(writeFile(file, text))

