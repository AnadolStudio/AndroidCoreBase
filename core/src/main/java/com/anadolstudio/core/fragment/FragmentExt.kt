package com.anadolstudio.core.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

fun <F : Fragment> F.withArgs(action: Bundle.() -> Unit): F = this.apply { arguments = Bundle().also(action) }
