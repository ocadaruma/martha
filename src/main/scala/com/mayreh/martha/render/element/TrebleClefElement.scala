package com.mayreh.martha.render.element

import com.mayreh.martha.render.Size

class TrebleClefElement(size: Size) {

  val element: scala.xml.Elem = {
    val Size(width, height) = size
    val w = width / 150
    val h = height / 400

      <path
      d={s"""M ${ w * 70.584 } ${ h * 265.615 }
            |C ${ w * 59.736 } ${ h * 259.121 }, ${ w * 54.609 } ${ h * 252.695 }, ${ w * 54.079 } ${ h * 237.04 }
            |C ${ w * 53.549 } ${ h * 221.384 }, ${ w * 73.765 } ${ h * 192.828 }, ${ w * 101.909 } ${ h * 195.309 }
            |C ${ w * 130.054 } ${ h * 197.789 }, ${ w * 150.0 } ${ h * 223.19 }, ${ w * 150.0 } ${ h * 247.258 }
            |C ${ w * 150.0 } ${ h * 271.325 }, ${ w * 113.845 } ${ h * 306.785 }, ${ w * 76.932 } ${ h * 306.364 }
            |C ${ w * 40.019 } ${ h * 305.943 }, ${ w * 0.051 } ${ h * 283.643 }, ${ w * 0.051 } ${ h * 237.04 }
            |C ${ w * -2.482 } ${ h * 184.943 }, ${ w * 90.417 } ${ h * 86.25 }, ${ w * 101.274 } ${ h * 72.297 }
            |C ${ w * 112.131 } ${ h * 58.344 }, ${ w * 119.658 } ${ h * 34.257 }, ${ w * 109.393 } ${ h * 18.21 }
            |C ${ w * 93.194 } ${ h * 24.326 }, ${ w * 63.282 } ${ h * 59.037 }, ${ w * 56.723 } ${ h * 96.009 }
            |C ${ w * 51.226 } ${ h * 122.611 }, ${ w * 118.467 } ${ h * 345.23 }, ${ w * 115.023 } ${ h * 367.916 }
            |C ${ w * 111.58 } ${ h * 390.602 }, ${ w * 94.477 } ${ h * 398.52 }, ${ w * 82.969 } ${ h * 399.991 }
            |C ${ w * 66.629 } ${ h * 400.227 }, ${ w * 33.329 } ${ h * 396.182 }, ${ w * 30.168 } ${ h * 368.652 }
            |C ${ w * 27.47 } ${ h * 351.97 }, ${ w * 42.204 } ${ h * 339.119 }, ${ w * 57.609 } ${ h * 339.534 }
            |C ${ w * 73.013 } ${ h * 339.948 }, ${ w * 82.305 } ${ h * 354.204 }, ${ w * 82.12 } ${ h * 364.399 }
            |C ${ w * 81.935 } ${ h * 374.593 }, ${ w * 76.018 } ${ h * 388.794 }, ${ w * 59.17 } ${ h * 389.094 }
            |C ${ w * 69.185 } ${ h * 394.603 }, ${ w * 100.001 } ${ h * 395.319 }, ${ w * 107.359 } ${ h * 367.859 }
            |C ${ w * 114.717 } ${ h * 340.399 }, ${ w * 44.721 } ${ h * 116.651 }, ${ w * 48.584 } ${ h * 79.325 }
            |C ${ w * 52.447 } ${ h * 42.0 }, ${ w * 73.652 } ${ h * 14.845 }, ${ w * 107.359 } ${ h * 0.0 }
            |C ${ w * 129.512 } ${ h * 28.311 }, ${ w * 130.426 } ${ h * 55.012 }, ${ w * 123.705 } ${ h * 78.913 }
            |C ${ w * 120.186 } ${ h * 91.428 }, ${ w * 111.676 } ${ h * 111.475 }, ${ w * 66.638 } ${ h * 154.52 }
            |C ${ w * 21.601 } ${ h * 197.564 }, ${ w * 15.745 } ${ h * 221.577 }, ${ w * 15.166 } ${ h * 249.795 }
            |C ${ w * 15.166 } ${ h * 276.584 }, ${ w * 55.031 } ${ h * 302.184 }, ${ w * 77.155 } ${ h * 301.203 }
            |C ${ w * 99.279 } ${ h * 300.222 }, ${ w * 126.27 } ${ h * 283.901 }, ${ w * 126.27 } ${ h * 252.4 }
            |C ${ w * 126.27 } ${ h * 236.194 }, ${ w * 113.523 } ${ h * 222.91 }, ${ w * 99.462 } ${ h * 219.897 }
            |C ${ w * 85.4 } ${ h * 216.884 }, ${ w * 72.003 } ${ h * 223.533 }, ${ w * 64.439 } ${ h * 234.112 }
            |C ${ w * 56.875 } ${ h * 244.692 }, ${ w * 62.945 } ${ h * 259.082 }, ${ w * 70.584 } ${ h * 265.615 }""".stripMargin}
      stroke="transparent"
      fill="black"
      />
  }
}

object TrebleClefElement {
  val yRatioAtG: Float = 0.65f
}
