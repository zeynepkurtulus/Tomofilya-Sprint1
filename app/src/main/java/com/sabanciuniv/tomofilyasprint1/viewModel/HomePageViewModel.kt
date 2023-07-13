package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sabanciuniv.tomofilyasprint1.R

import com.sabanciuniv.tomofilyasprint1.model.HomeGetAll.HomeGetAllResponse
import com.sabanciuniv.tomofilyasprint1.network.HomeSearchRequest
import com.sabanciuniv.tomofilyasprint1.network.PagingClass
import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageViewModel() : ViewModel() {
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient
    private var responseBodyGen :HomeGetAllResponse? = null
    val lastAnnouncementDetails = MutableLiveData<String>()
    val announceBtnName = MutableLiveData<String>()
    val btnUrl = MutableLiveData<String>()
    val iconUrls: MutableList<String> = mutableListOf()
    private val buttonIds : MutableMap<String, Int> = mutableMapOf()

    fun setContext(ctx: Context){
        this.ctx = ctx
        retrofitClient = RetrofitClient(ctx)
        buttonIds["button0"] = R.id.button1
        buttonIds["button1"] = R.id.button2
        buttonIds["button2"] = R.id.button3
        buttonIds["button3"] = R.id.button4
        buttonIds["button4"] = R.id.button5
        buttonIds["button5"] = R.id.button6
    }


    fun getData() {
        try {
            val retrofitBuilder = retrofitClient.createAPIRequest()
            val retrofitData = retrofitBuilder.homePageGetAllData()
            Log.e("Home process", "Inside Home VM going...")
            retrofitData.enqueue(object: Callback<HomeGetAllResponse> {
                override fun onResponse(call: Call<HomeGetAllResponse>, response: Response<HomeGetAllResponse>) {
                    val responseBody = response.body()
                    Log.e("response body" , responseBody?.success.toString())
                    responseBodyGen = responseBody
                    if(responseBody?.success  == true){
                        //TODO get the daha at place it


                        HomePageHandler(responseBody)
                    }
                    else{
                        Log.e("Home api error)" , responseBody?.success.toString())
                        Log.e("Home api error)" , responseBody?.message.toString())


                    }
                }

                override fun onFailure(call: Call<HomeGetAllResponse>, t: Throwable) {
                    Log.e("Login error: ", t.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("Home api error step 1: ", e.toString())
            // Handle the exception here (e.g. log it or display an error message)
        }
    }


    fun HomePageHandler(responseBody : HomeGetAllResponse){


        val data = responseBody.data
        Log.e("tag", "logo name for garage: " + data.garages[0].logoUrl)
        Log.e("tag", "logo name for garage: " + data.garages[1].logoUrl)
        Log.e("tag", "logo name for garage: " + data.garages[2].logoUrl)
        Log.e("tag", "logo name for garage: " + data.garages[3].logoUrl)
        Log.e("tag", "logo name for categories: " + data.categories[0].iconUrl)
        Log.e("tag", "logo name for categories: " + data.categories[1].iconUrl)
        Log.e("tag", "logo name for categories: " + data.categories[2].iconUrl)
        Log.e("tag", "logo name for categories: " + data.categories[3].iconUrl)
        Log.e("tag", "logo name for categories: " + data.categories[4].iconUrl)
        Log.e("tag", "logo name for categories: " + data.categories[5].iconUrl)
        makeEvent()
        makeVideo()
        makeDict()
        makeAnnouncement()
        makeGarages()

        makeCategories(0, responseBody)
        makeCategories(1, responseBody)
        makeCategories(2,responseBody)
        makeCategories(3,responseBody)
        makeCategories(4, responseBody)
        makeCategories(5, responseBody)
    }


   fun makeEvent(){
       val eventName = (ctx as Activity)?.findViewById<TextView>(R.id.eventNameTxt!!)
       val eventLoc = (ctx as Activity)?.findViewById<TextView>(R.id.eventLoc!!)
       val eventDesc = (ctx as Activity)?.findViewById<TextView>(R.id.eventDesc!!)
       val eventDate = (ctx as Activity)?.findViewById<TextView>(R.id.eventDate!!)


       eventName?.text = responseBodyGen?.data?.lastEvent?.name.toString()
       eventLoc?.text = responseBodyGen?.data?.lastEvent?.lat.toString() + "   " +  responseBodyGen?.data?.lastEvent?.lng.toString()
       eventDesc?.text = responseBodyGen?.data?.lastEvent?.description.toString()
       eventDate?.text = responseBodyGen?.data?.lastEvent?.startDate.toString()

   }

    fun makeVideo(){
        val videoTitle = (ctx as Activity)?.findViewById<Button>(R.id.btnVideoHeader!!)
        videoTitle?.text = responseBodyGen?.data?.lastVideo?.title.toString()
        videoTitle?.setOnClickListener {
            val url = responseBodyGen?.data?.lastVideo?.videoUrl
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            ctx.startActivity(intent)

        }
    }

    fun makeAnnouncement(){
        val announcementHeader = (ctx as Activity)?.findViewById<Button>(R.id.btnAnnounceHeader!!)
        val annoucementDetails = (ctx as Activity)?.findViewById<TextView>(R.id.lastAnnouncementDetails!!)

        announcementHeader?.text = responseBodyGen?.data?.lastAnnouncement?.name.toString()
        annoucementDetails?.text = responseBodyGen?.data?.lastAnnouncement?.description.toString()
        announcementHeader?.setOnClickListener {
            val url = responseBodyGen?.data?.lastAnnouncement?.buttonUrl
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            ctx.startActivity(intent)
        }

    }

    fun makeDict(){
        val dictWord = (ctx as Activity)?.findViewById<TextView>(R.id.dictionaryTitleTv!!)
        val dictDesc = (ctx as Activity)?.findViewById<TextView>(R.id.dictionaryDescTv!!)
        Log.e("tag", "word in dict: " + responseBodyGen?.data?.randomGlossary?.name.toString())
        dictWord?.text = responseBodyGen?.data?.randomGlossary?.name.toString()
        dictDesc?.text = responseBodyGen?.data?.randomGlossary?.description.toString()


    }

    fun setButtonUrlIcon(button: Button, buttonId:Int, iconUrl: String){
        val packageName = button.context.packageName
        val drawableId = button.context.resources.getIdentifier(iconUrl, "drawable", packageName)
        val draw = ContextCompat.getDrawable(button.context, drawableId)
        button.setCompoundDrawablesWithIntrinsicBounds(null, draw, null, null);
    }

    fun makeGarages(){
        var garageName = responseBodyGen?.data?.garages?.get(0)?.userName.toString()
        //var garageLogo =  responseBodyGen?.data?.garages?.get(0)?.logoUrl.toString()
        if (garageName == null) garageName = null.toString()
        //if (garageLogo == null) garageLogo = null.toString()
        if(garageName == null || garageName == "null") return
        //garageLogo = garageLogo.removeSuffix(".png")
        //setButtonUrlIcon(gBtn1!! , R.id.garageBtn1, garageLogo.toString())


    }

    fun makeCategories(id : Int, response : HomeGetAllResponse){
        val name = "button" + id.toString()
        if(buttonIds[name] == null) return
        val id1 = buttonIds[name]
        val btnInUi =  (ctx as Activity)?.findViewById<Button>(id1!!)
        val url1 = responseBodyGen?.data?.categories?.get(id)?.iconUrl?.removeSuffix(".png")
        Log.e("tag", "Url of cat btn: " + url1.toString())
        setButtonUrlIcon(btnInUi!!, id1!!, url1.toString())
    }





}







