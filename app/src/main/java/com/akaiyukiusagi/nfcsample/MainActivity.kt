package com.akaiyukiusagi.nfcsample

import android.nfc.NfcAdapter
import android.nfc.NfcAdapter.*
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nfcAdapter = getDefaultAdapter(this) // 読み取り開始
        nfcAdapter.enableReaderMode(this, NfcReaderCallback(),FLAG_READER_NFC_F,null) // これだとNFCのタイプ一つ分しか判定できなそう
    }

    override fun onDestroy() {
        super.onDestroy()
        nfcAdapter.disableReaderMode(this) // 読み取り終了
    }

    // Callback Class
    private class NfcReaderCallback : ReaderCallback {
        override fun onTagDiscovered(tag: Tag) {
            val idm = tag.id
            val idmString: String = bytesToHexString(idm)
            Log.d("IDm", idmString)
        }

        // bytes列を16進数文字列に変換
        private fun bytesToHexString(bytes: ByteArray): String {
            val sb = StringBuilder()
            val formatter = Formatter(sb)
            for (b in bytes) { formatter.format("%02x", b) }
            return sb.toString().toUpperCase(Locale.getDefault())
        }
    }
}

// 参考：https://qiita.com/zaburo/items/6a34dfd8f87d7ffba56a