package org.lynxz.webhook

import java.text.DecimalFormat

fun Double.yuan(): String = DecimalFormat("0.##").format(this)

