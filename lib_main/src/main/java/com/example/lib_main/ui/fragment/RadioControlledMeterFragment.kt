package com.example.lib_main.ui.fragment

import android.os.Bundle
import com.example.lib_base.base.BaseFragment
import com.example.lib_main.R
import com.example.lib_main.databinding.FragmentRadioControlledMeterBinding
import com.example.lib_main.viewmodel.RadioControlledMeterViewModel
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.math.cos

/**
 * @CreateDate: 2024/10/9 21:15
 * @Author: 青柠
 * @Description: 电波表
 */
class RadioControlledMeterFragment :
    BaseFragment<RadioControlledMeterViewModel, FragmentRadioControlledMeterBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()

        setTranslucent(mDatabind.flTranslucent)
    }


    fun generateBPCSignal(dateTime: LocalDateTime): ByteArray {
        val sampleRate = 68500
        val carrierFrequency = 6850.0 * 2 // 载波频率
        val frameDuration = 20 // 每帧时长，单位：秒
        val frameSize = (frameDuration * sampleRate).toInt()
        val buffer = ByteBuffer.allocate(frameSize * 2) // 每个样本 2 字节

        val bpcCode = generateBPCCodes(dateTime) // 生成 BPC 编码

        for (i in 0 until frameSize) {
            val timeInSeconds = i.toDouble() / sampleRate
            val signalValue = if (shouldTransmitSignal(timeInSeconds, bpcCode)) {
                (Short.MAX_VALUE * cos(2 * Math.PI * carrierFrequency * timeInSeconds)).toInt()
                    .toShort()
            } else {
                0.toShort()
            }
            buffer.putShort(signalValue)
        }

        return buffer.array()
    }

    fun playBPCSignal(signal: ByteArray) {
        val sampleRate = 68500
        val audioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            signal.size,
            AudioTrack.MODE_STATIC
        )

        audioTrack.write(signal, 0, signal.size)
        audioTrack.play()
        
    }

    fun generateBPCCodes(dateTime: LocalDateTime): String {
        val second = dateTime.second
        val minute = dateTime.minute
        val hour = dateTime.hour
        val dayOfMonth = dateTime.dayOfMonth
        val month = dateTime.monthValue
        val year = dateTime.year % 100 // 取年份后两位

        // 构建 BPC 编码字符串
        val bpcCode = StringBuilder()

        // 分钟、小时、日期、月份、年份
        bpcCode.append(encodeBCD(minute, 6))
        bpcCode.append(encodeBCD(hour, 4))
        bpcCode.append(encodeBCD(dayOfMonth, 6))
        bpcCode.append(encodeBCD(month, 4))
        bpcCode.append(encodeBCD(year, 6))

        // 奇偶校验位
        bpcCode.append(calculateParity(bpcCode.toString()))

        // 预留位和起始位
        bpcCode.insert(0, "001")

        return bpcCode.toString()
    }

    fun encodeBCD(value: Int, length: Int): String {
        val binaryString = Integer.toBinaryString(value)
        return binaryString.padStart(length, '0')
    }

    fun calculateParity(data: String): String {
        val parity = data.count { it == '1' } % 2
        return parity.toString()
    }

    fun shouldTransmitSignal(timeInSeconds: Double, bpcCode: String): Boolean {
        val currentBitIndex = ((timeInSeconds % 20) * 10).toInt() // 获取当前比特索引
        return if (currentBitIndex in bpcCode.indices) {
            bpcCode[currentBitIndex] == '1' // 如果当前比特为 1，则发送信号
        } else {
            false // 否则不发送信号
        }
    }

    inner class ProxyClick {
        fun toStart() {
            val dateTime = LocalDateTime.now(ZoneOffset.of("+8")) // 使用中国时区
            val signal = generateBPCSignal(dateTime)
            playBPCSignal(signal)
        }
    }
}