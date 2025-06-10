

// 6. Implement the zipWith function in terms of the toy model of a ZIO effect. The
// function should return an effect that sequentially composes the specified effects,
// merging their results with the specified user-defined function.

final case class ZIO[-R, +E, +A](run: R => Either[E, A])

def zipWith[R, E, A, B, C](
  self: ZIO[R, E, A],
  that: ZIO[R, E, B]
)(f: (A, B) => C): ZIO[R, E, C] = {
  ZIO( r =>
    for {
      a <- self.run(r)
      b <- that.run(r)
    } yield f(a, b)
  )
}
