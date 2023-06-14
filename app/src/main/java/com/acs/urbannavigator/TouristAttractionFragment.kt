package com.acs.urbannavigator

import android.accessibilityservice.GestureDescription.StrokeDescription
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.acs.urbannavigator.databinding.FragmentTouristAttractionBinding
import com.acs.urbannavigator.models.TourList.TourListItem
import com.squareup.picasso.Picasso

class TouristAttractionFragment : Fragment() {

    private lateinit var binding: FragmentTouristAttractionBinding
    private var tourListItem: TourListItem? = null
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()
    private var pause:Boolean = false
    private var audioUrl:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        audioUrl = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
        mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
//        mediaPlayer.setDataSource(audioUrl)
//        mediaPlayer.prepare()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTouristAttractionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleTouristAttraction()
    }

    private fun createMediaPlayer(){

        binding.playBtn.setOnClickListener{
            if(pause){
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
                pause = false
                Toast.makeText(requireContext(),"media playing", Toast.LENGTH_SHORT).show()
            }else{
                mediaPlayer.start()
                Toast.makeText(requireContext(),"media playing", Toast.LENGTH_SHORT).show()

            }
            initializeSeekBar()
            binding.playBtn.isEnabled = false
            binding.pauseBtn.isEnabled = true
            binding.stopBtn.isEnabled = true

            mediaPlayer.setOnCompletionListener {
                binding.playBtn.isEnabled = true
                binding.pauseBtn.isEnabled = false
                binding.stopBtn.isEnabled = false
                Toast.makeText(requireContext(),"end", Toast.LENGTH_SHORT).show()
            }
        }
        // Pause the media player
        binding.pauseBtn.setOnClickListener {
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                pause = true
                binding.playBtn.isEnabled = true
                binding.pauseBtn.isEnabled = false
                binding.stopBtn.isEnabled = true
                Toast.makeText(requireContext(),"media pause", Toast.LENGTH_SHORT).show()
            }
        }
        // Stop the media player
        binding.stopBtn.setOnClickListener{
            if(mediaPlayer.isPlaying || pause.equals(true)){
                pause = false
                binding.seekBar.setProgress(0)
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                handler.removeCallbacks(runnable)

                binding.playBtn.isEnabled = true
                binding.pauseBtn.isEnabled = false
                binding.stopBtn.isEnabled = false
                binding.tvPass.text = ""
                binding.tvDue.text = ""
                Toast.makeText(requireContext(),"media stop", Toast.LENGTH_SHORT).show()
            }
        }
        // Seek bar change listener
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer.seekTo(i * 1000)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    }

    private fun initializeSeekBar() {
        binding.seekBar.max = mediaPlayer.seconds

        runnable = Runnable {
            binding.seekBar.progress = mediaPlayer.currentSeconds

            binding.tvPass.text = "${mediaPlayer.currentSeconds} sec"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            binding.tvDue.text = "$diff sec"

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }

    private fun getTouristAttraction() {
        tourListItem?.let { attraction ->
            val descriptionTextView: TextView = binding.touristAttractionDescription
            val imageView: ImageView = binding.touristAttractionImage
            val titleTextView: TextView = binding.touristAttractionTitle

            descriptionTextView.text = Html.fromHtml(attraction.content[0].desc)
            titleTextView.text = attraction.content[0].title

            if (!attraction.content[0].images.isNullOrEmpty()) {
                val imagePath =
                    "https://media.izi.travel/" + tourListItem!!.contentProvider.uuid + "/" + tourListItem!!.content[0].images[0].uuid + "_800x600.jpg"
                Picasso.get().load(imagePath).into(imageView)
            }

            if (!attraction.content[0].audio.isNullOrEmpty()) {
                audioUrl =
                    "https://media.izi.travel/" + tourListItem!!.contentProvider.uuid + "/" + tourListItem!!.content[0].audio[0].uuid + ".m4a"
                mediaPlayer.setDataSource(audioUrl)
                mediaPlayer.prepare()
                createMediaPlayer()
            }

        }
    }

    private fun getBundleTouristAttraction() {
        setFragmentResultListener("touristAttractionKey") { _, bundle ->
            val result = bundle.getSerializable("touristAttraction") as? TourListItem
            tourListItem = result
            getTouristAttraction()
        }
    }


}
// Creating an extension property to get the media player time duration in seconds
val MediaPlayer.seconds:Int
    get() {
        return this.duration / 1000
    }
// Creating an extension property to get media player current position in seconds
val MediaPlayer.currentSeconds:Int
    get() {
        return this.currentPosition/1000
    }
