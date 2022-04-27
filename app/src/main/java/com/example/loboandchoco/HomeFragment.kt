package com.example.loboandchoco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loboandchoco.databinding.FragmentHomeBinding
import com.example.loboandchoco.databinding.FragmentMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var database: DatabaseReference

    private var feedList:  ArrayList<Feed> = arrayListOf()

    private var storyList: ArrayList<Story> = arrayListOf(
        Story("Choco","data:image/png;base64,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"),
        Story("Choco",""),
        Story("Choco",""),
        Story("Choco",""),
        Story("Choco",""),
        Story("Choco",""),
        Story("Choco",""),
        Story("Choco",""),
        Story("Choco",""),
        Story("Choco",""),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.database
        database = db.getReference("FeedList")

        val addValueEventListener = database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val values = snapshot.value as ArrayList<HashMap<String, Any>>?
                for(i:Int in 1 until(values?.size ?:0)){
                    val data = values?.get(i)
                    feedList.add(
                        Feed(data?.get("userId") as String,
                            data?.get("imageUrl") as String,
                            data?.get("profileImageUrl") as String,
                            data?.get("likeCount") as Long,
                            data?.get("isLike") as Boolean,
                            data?.get("isBookmark") as Boolean)
                    )
                }
                binding.homeRvFeed.adapter?.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.homeRvFeed.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.homeRvFeed.adapter=FeedAdapter(activity as MainActivity, feedList)
        binding.homeRvFeed.isNestedScrollingEnabled = false

        binding.homeRvStrory.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.homeRvStrory.adapter=StroyAdapter(activity as MainActivity, storyList)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}