package com.example.demo

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.demo.extensions.launchActivity
import com.example.demo.extensions.toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomDialogFragment : BottomSheetDialogFragment() {
    private lateinit var webView: WebView
    private lateinit var urlReceive: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
        val bundleReceive = arguments
        if (bundleReceive != null) {
            urlReceive = bundleReceive["url"] as String
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_dialog_fragment, null)
        bottomSheetDialog.setContentView(view)
        webView = view.findViewById(R.id.webView)
        clickEventWed(view)
        return bottomSheetDialog
    }

    private fun loadJs(webView: WebView) {
        webView.loadUrl(
            """javascript:(function f() {
        var button = document.getElementsByTagName('button');
        var input = document.getElementsByTagName('input');
        for (var i = 0, n = button.length; i < n; i++) {
          if (button[i].getAttribute('class') === 'btn btn-primary') {
            button[i].setAttribute('onclick', 'Android.primary()');
          }
          if (input[i].getAttribute('class') === 'btn btn-secondary') {
            input[i].setAttribute('onclick', 'Android.secondary()');
          }
          if (button[i].getAttribute('class') === 'my-1 btn btn-success btn-block') {
            button[i].setAttribute('onclick', 'Android.success()');
          }
        }
      })()"""
        )
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun clickEventWed(view: View) {
        val webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                loadJs(view)
            }
        }
        webView.webViewClient = webViewClient
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(object : Any() {
            @JavascriptInterface
            fun primary() {
                context?.toast("BLUE")
            }

            @JavascriptInterface
            fun secondary() {
                context?.toast("GRAY")
            }

            @JavascriptInterface
            fun success() {
                context?.toast("GREEN")
            }
        }, "Android")
        webView.loadUrl(urlReceive)
    }

    companion object {
        fun newInstance(url: String?): BottomDialogFragment {
            val bottomDialogFragment = BottomDialogFragment()
            val bundle = Bundle()
            bundle.putString("url", url)
            bottomDialogFragment.arguments = bundle
            return bottomDialogFragment
        }
    }
}