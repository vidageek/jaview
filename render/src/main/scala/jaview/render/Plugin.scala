package jaview.render

import jaview.CachedJaview
import jaview.CachedJaview

class Plugin(cache : CachedJaview) {

  def apply(view : String) = new {
    def apply() = cache(view)()
    def apply[T1](t1 : T1) = cache(view)(t1)
    def apply[T1, T2](t1 : T1, t2 : T2) = cache(view)(t1, t2)
    def apply[T1, T2, T3](t1 : T1, t2 : T2, t3 : T3) = cache(view)(t1, t2, t3)
    def apply[T1, T2, T3, T4](t1 : T1, t2 : T2, t3 : T3, t4 : T4) = cache(view)(t1, t2, t3, t4)
    def apply[T1, T2, T3, T4, T5](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5) = cache(view)(t1, t2, t3, t4, t5)
    def apply[T1, T2, T3, T4, T5, T6](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6) = cache(view)(t1, t2, t3, t4, t5, t6)
    def apply[T1, T2, T3, T4, T5, T6, T7](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7) = cache(view)(t1, t2, t3, t4, t5, t6, t7)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13, t14 : T14) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13, t14 : T14, t15 : T15) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13, t14 : T14, t15 : T15, t16 : T16) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13, t14 : T14, t15 : T15, t16 : T16, t17 : T17) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13, t14 : T14, t15 : T15, t16 : T16, t17 : T17, t18 : T18) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13, t14 : T14, t15 : T15, t16 : T16, t17 : T17, t18 : T18, t19 : T19) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13, t14 : T14, t15 : T15, t16 : T16, t17 : T17, t18 : T18, t19 : T19, t20 : T20) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13, t14 : T14, t15 : T15, t16 : T16, t17 : T17, t18 : T18, t19 : T19, t20 : T20, t21 : T21) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21)
    def apply[T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22](t1 : T1, t2 : T2, t3 : T3, t4 : T4, t5 : T5, t6 : T6, t7 : T7, t8 : T8, t9 : T9, t10 : T10, t11 : T11, t12 : T12, t13 : T13, t14 : T14, t15 : T15, t16 : T16, t17 : T17, t18 : T18, t19 : T19, t20 : T20, t21 : T21, t22 : T22) = cache(view)(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22)

  }

}

object GenApplies extends App {
  (1 to 22).foreach { i =>
    val l = 1 to i
    val types = l.map("T" + _).mkString(",")
    val params = l.map(e => s"t$e:T$e").mkString(",")
    val names = l.map("t" + _).mkString(",")
    println(s"def apply[$types]($params) = cache(view)($names)")
  }
}