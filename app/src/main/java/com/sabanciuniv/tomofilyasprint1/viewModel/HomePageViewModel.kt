package com.sabanciuniv.tomofilyasprint1.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.net.Uri
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide.with
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.with
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.sabanciuniv.tomofilyasprint1.R
import com.sabanciuniv.tomofilyasprint1.model.HomeGetAll.HomeGetAllResponse

import com.sabanciuniv.tomofilyasprint1.retrofitHandler.RetrofitClient
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePageViewModel() : ViewModel() {
    private lateinit var ctx: Context
    private lateinit var retrofitClient: RetrofitClient
    private var responseBodyGen :HomeGetAllResponse? = null
    val announceBtnName = MutableLiveData<String>()
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

        makeCategories(0)
        makeCategories(1)
        makeCategories(2)
        makeCategories(3)
        makeCategories(4)
        makeCategories(5)
        loadImage()
        makeProducts()
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
        val dictImage = (ctx as Activity)?.findViewById<ImageView>(R.id.dictionaryIv!!)
        Log.e("tag", "word in dict: " + responseBodyGen?.data?.randomGlossary?.name.toString())
        dictWord?.text = responseBodyGen?.data?.randomGlossary?.name.toString()
        dictDesc?.text = responseBodyGen?.data?.randomGlossary?.description.toString()

        val url = responseBodyGen?.data?.randomGlossary?.photoUrl.toString()
        Log.e("tag", "Dict Url: " + url.toString())
        Picasso.with(ctx).load(url).
        fit().into(dictImage);



    }

    fun setButtonUrlIcon(button: Button, buttonId:Int, iconUrl: String){
        val packageName = button.context.packageName
        val drawableId = button.context.resources.getIdentifier(iconUrl, "drawable", packageName)
        val draw = ContextCompat.getDrawable(button.context, drawableId)
        button.setCompoundDrawablesWithIntrinsicBounds(null, draw, null, null);
    }

    fun makeGarages(){
        var garageName = (ctx as Activity)?.findViewById<TextView>(R.id.nameholder1)
        var garageName2 = (ctx as Activity)?.findViewById<TextView>(R.id.nameholder2)
        var garageName3 = (ctx as Activity)?.findViewById<TextView>(R.id.nameholder3)
        var garageName4 = (ctx as Activity)?.findViewById<TextView>(R.id.nameholder4)
        var garageName5 = (ctx as Activity)?.findViewById<TextView>(R.id.nameholder5)
        garageName?.text = responseBodyGen?.data?.garages?.get(0)?.userName.toString()
        garageName2?.text = responseBodyGen?.data?.garages?.get(1)?.userName.toString()
        garageName3?.text = responseBodyGen?.data?.garages?.get(2)?.userName.toString()
        garageName4?.text = responseBodyGen?.data?.garages?.get(3)?.userName.toString()
        garageName5?.text = responseBodyGen?.data?.garages?.get(4)?.userName.toString()
        //var garageLogo =  responseBodyGen?.data?.garages?.get(0)?.logoUrl.toString()
        //if (garageLogo == null) garageLogo = null.toString()
        //garageLogo = garageLogo.removeSuffix(".png")
        //setButtonUrlIcon(gBtn1!! , R.id.garageBtn1, garageLogo.toString())
        val baseUrl = "https://tomofilyastorage.blob.core.windows.net/garagelogos/"
        val res1 = responseBodyGen?.data?.garages?.get(0)?.logoUrl.toString()
        val res2 = responseBodyGen?.data?.garages?.get(1)?.logoUrl.toString()
        val res3 = responseBodyGen?.data?.garages?.get(2)?.logoUrl.toString()
        val res4 = responseBodyGen?.data?.garages?.get(3)?.logoUrl.toString()
        val res5 = responseBodyGen?.data?.garages?.get(4)?.logoUrl.toString()

        val logo1 = (ctx as Activity)?.findViewById<ImageButton>(R.id.garageBtn1)
        val logo2 = (ctx as Activity)?.findViewById<ImageButton>(R.id.garageBtn2)
        val logo3 = (ctx as Activity)?.findViewById<ImageButton>(R.id.garageBtn3)
        val logo4 = (ctx as Activity)?.findViewById<ImageButton>(R.id.garageBtn4)
        val logo5 = (ctx as Activity)?.findViewById<ImageButton>(R.id.garageBtn5)
        val url1 = baseUrl + res1
        val url2 = baseUrl + res2
        val url3 = baseUrl + res3
        val url4 = baseUrl + res4
        val url5 = baseUrl + res5

        Log.e("tag", "Garage url: " + url1)
        Log.e("tag", "Garage name: " + garageName)
        Picasso.with(ctx).load(url1).
        fit().into(logo1)
        Picasso.with(ctx).load(url2).
        fit().into(logo2)
        Picasso.with(ctx).load(url3).
        fit().into(logo3)
        Picasso.with(ctx).load(url4).
        fit().into(logo4);
        Picasso.with(ctx).load(url5).
        fit().into(logo5);
    }

    fun makeCategories(id : Int){
        /*
        val name = "button" + id.toString()
        if(buttonIds[name] == null) return
        val id1 = buttonIds[name]
        val btnInUi =  (ctx as Activity)?.findViewById<Button>(id1!!)
        val url1 = responseBodyGen?.data?.categories?.get(id)?.iconUrl?.removeSuffix(".png")
        Log.e("tag", "Url of cat btn: " + url1.toString())
        setButtonUrlIcon(btnInUi!!, id1!!, url1.toString())

         */
        val baseUrl = "https://tomofilyastorage.blob.core.windows.net/categoryicons/"
        val cat1 = responseBodyGen?.data?.categories?.get(0)?.iconUrl
        val cat2 = responseBodyGen?.data?.categories?.get(1)?.iconUrl
        val cat3 = responseBodyGen?.data?.categories?.get(2)?.iconUrl
        val cat4 = responseBodyGen?.data?.categories?.get(3)?.iconUrl
        val cat5 = responseBodyGen?.data?.categories?.get(4)?.iconUrl
        val cat6 = responseBodyGen?.data?.categories?.get(5)?.iconUrl

        val btn1 = (ctx as Activity)?.findViewById<ImageButton>(R.id.button1)
        val btn2 = (ctx as Activity)?.findViewById<ImageButton>(R.id.button2)
        val btn3 = (ctx as Activity)?.findViewById<ImageButton>(R.id.button3)
        val btn4 = (ctx as Activity)?.findViewById<ImageButton>(R.id.button4)
        val btn5 = (ctx as Activity)?.findViewById<ImageButton>(R.id.button5)
        val btn6 = (ctx as Activity)?.findViewById<ImageButton>(R.id.button6)

        val url1 = baseUrl + cat1
        val url2 = baseUrl + cat2
        val url3 = baseUrl + cat3
        val url4 = baseUrl + cat4
        val url5 = baseUrl + cat5
        val url6 = baseUrl + cat6

        Picasso.with(ctx).load(url1).
        fit().into(btn1);
        Picasso.with(ctx).load(url2).
        fit().into(btn2);
        Picasso.with(ctx).load(url3).
        fit().into(btn3);
        Picasso.with(ctx).load(url4).
        fit().into(btn4);
        Picasso.with(ctx).load(url5).
        fit().into(btn5);
        Picasso.with(ctx).load(url6).
        fit().into(btn6);



        val name1 = (ctx as Activity)?.findViewById<TextView>(R.id.spareText)
        name1?.text =  responseBodyGen?.data?.categories?.get(0)?.name
        val name2 = (ctx as Activity)?.findViewById<TextView>(R.id.tireText)
        name2?.text =  responseBodyGen?.data?.categories?.get(1)?.name
        val name3 = (ctx as Activity)?.findViewById<TextView>(R.id.fixText)
        name3?.text =  responseBodyGen?.data?.categories?.get(2)?.name
        val name4 = (ctx as Activity)?.findViewById<TextView>(R.id.toolText)
        name4?.text =  responseBodyGen?.data?.categories?.get(3)?.name
        val name5 = (ctx as Activity)?.findViewById<TextView>(R.id.tomoText)
        name5?.text =  responseBodyGen?.data?.categories?.get(4)?.name
        val name6 = (ctx as Activity)?.findViewById<TextView>(R.id.musicText)
        name6?.text =  responseBodyGen?.data?.categories?.get(5)?.name

    }

    fun loadImage(){
        val baseImageUrl = "https://tomofilyastorage.blob.core.windows.net/productphotos/"
        val res1 = responseBodyGen?.data?.products?.get(0)?.photoUrl.toString()
        val res2 =  responseBodyGen?.data?.products?.get(1)?.photoUrl.toString()
        val res3 =  responseBodyGen?.data?.products?.get(2)?.photoUrl.toString()
        val res4 =  responseBodyGen?.data?.products?.get(3)?.photoUrl.toString()
        val res5 =  responseBodyGen?.data?.products?.get(4)?.photoUrl.toString()
        val res6 =  responseBodyGen?.data?.products?.get(5)?.photoUrl.toString()
        val prod1Url = baseImageUrl + res1
        val prod2Url = baseImageUrl + res2
        val prod3Url = baseImageUrl + res3
        val prod4Url = baseImageUrl + res4
        val prod5Url = baseImageUrl + res5
        val prod6Url = baseImageUrl + res6
        val prod1 = (ctx as Activity)?.findViewById<ImageView>(R.id.prod1)
        val prod2 = (ctx as Activity)?.findViewById<ImageView>(R.id.prod2)
        val prod3 = (ctx as Activity)?.findViewById<ImageView>(R.id.prod3)
        val prod4 = (ctx as Activity)?.findViewById<ImageView>(R.id.prod4)
        val prod5 = (ctx as Activity)?.findViewById<ImageView>(R.id.prod5)
        val prod6 = (ctx as Activity)?.findViewById<ImageView>(R.id.prod6)
        Picasso.with(ctx).load(prod1Url).
        fit().into(prod1);

        Picasso.with(ctx).load(prod2Url).
        fit().into(prod2);

        Picasso.with(ctx).load(prod3Url).
        fit().into(prod3);

        Picasso.with(ctx).load(prod4Url).
        fit().into(prod4);

        Picasso.with(ctx).load(prod5Url).
        fit().into(prod5);

        Picasso.with(ctx).load(prod6Url).
        fit().into(prod6);


    }

    fun makeProducts(){
        val brand1 = (ctx as Activity)?.findViewById<TextView>(R.id.car_brand)
        brand1?.text = responseBodyGen?.data?.products?.get(0)?.brandName
        val brand2 = (ctx as Activity)?.findViewById<TextView>(R.id.car_brand2)
        brand2?.text = responseBodyGen?.data?.products?.get(1)?.brandName
        val brand3 = (ctx as Activity)?.findViewById<TextView>(R.id.car_brand3)
        brand3?.text = responseBodyGen?.data?.products?.get(2)?.brandName
        val brand4 = (ctx as Activity)?.findViewById<TextView>(R.id.car_brand4)
        brand4?.text = responseBodyGen?.data?.products?.get(3)?.brandName
        val brand5 = (ctx as Activity)?.findViewById<TextView>(R.id.car_brand5)
        brand5?.text = responseBodyGen?.data?.products?.get(4)?.brandName
        val brand6 = (ctx as Activity)?.findViewById<TextView>(R.id.car_brand6)
        brand6?.text = responseBodyGen?.data?.products?.get(5)?.brandName

        val part1 = (ctx as Activity)?.findViewById<TextView>(R.id.car_part)
        part1?.text = responseBodyGen?.data?.products?.get(0)?.title
        val part2 = (ctx as Activity)?.findViewById<TextView>(R.id. car_part2)
        part2?.text = responseBodyGen?.data?.products?.get(1)?.title
        val part3 = (ctx as Activity)?.findViewById<TextView>(R.id.car_part3)
        part3?.text = responseBodyGen?.data?.products?.get(2)?.title
        val part4 = (ctx as Activity)?.findViewById<TextView>(R.id.car_part4)
        part4?.text = responseBodyGen?.data?.products?.get(3)?.title
        val part5 = (ctx as Activity)?.findViewById<TextView>(R.id.car_part5)
        part5?.text = responseBodyGen?.data?.products?.get(4)?.title
        val part6 = (ctx as Activity)?.findViewById<TextView>(R.id.car_part6)
        part6?.text = responseBodyGen?.data?.products?.get(5)?.title

        val price1 = (ctx as Activity)?.findViewById<TextView>(R.id.car_price)
        price1?.text = responseBodyGen?.data?.products?.get(0)?.price.toString()
        val price2 = (ctx as Activity)?.findViewById<TextView>(R.id.crossed_price)
        price2?.text = responseBodyGen?.data?.products?.get(1)?.price.toString()
        val price3 = (ctx as Activity)?.findViewById<TextView>(R.id.car_price3)
        price3?.text = responseBodyGen?.data?.products?.get(2)?.price.toString()
        val price4 = (ctx as Activity)?.findViewById<TextView>(R.id.car_price4)
        price4?.text = responseBodyGen?.data?.products?.get(3)?.price.toString()
        val price5 = (ctx as Activity)?.findViewById<TextView>(R.id.car_price5)
        price5?.text = responseBodyGen?.data?.products?.get(4)?.price.toString()
        val price6 = (ctx as Activity)?.findViewById<TextView>(R.id.car_price6)
        price6?.text = responseBodyGen?.data?.products?.get(5)?.price.toString()
    }



}







