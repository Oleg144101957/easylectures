package com.vishnevskiypro.easylectures

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.vishnevskiypro.easylectures.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private companion object {
        const val URL =
            "https://www.wallpaperup.com/uploads/wallpapers/2014/10/21/489485/b807c2282ab0a491bd5c5c1051c6d312-1000.jpg"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }



        val fullText = getString(R.string.agreement_full_text)
        val confidential = getString(R.string.confidential_info)
        val policy = getString(R.string.privacy_policy)
        val spannableString = SpannableString(fullText)

        val confidentialClickable = MyClickableSpan{
            Snackbar.make(it, "Go to link 1", Snackbar.LENGTH_LONG).show()
        }

        val policyClickable = MyClickableSpan{
            Snackbar.make(it, "Go to link 2", Snackbar.LENGTH_LONG).show()
        }

        spannableString.setSpan(
            confidentialClickable,
            fullText.indexOf(confidential),
            fullText.indexOf(confidential) + confidential.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            policyClickable,
            fullText.indexOf(policy),
            fullText.indexOf(policy) + policy.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        
        val netImage = NetImage(URL, object : ImageCallback{
            override fun success(bitmap: Bitmap) = runOnUiThread {
                binding.myImage.setImageBitmap(bitmap)
            }

            override fun failed() = runOnUiThread {
                Snackbar.make(binding.myImage, "failed", Snackbar.LENGTH_SHORT).show()
            }
        })

        netImage.start()

//        binding.tvContainer.run {
//            text = spannableString
//            movementMethod = LinkMovementMethod.getInstance()
//            highlightColor = Color.TRANSPARENT
//        }


    }
}