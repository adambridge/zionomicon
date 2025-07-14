package exercises

import exercises.Ch01Exercise01.{readFile, readFileZio}
import exercises.Ch01Exercise02.{writeFile, writeFileZio}
import zio.ZIO

object Ch01Exercise03 {
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
}
