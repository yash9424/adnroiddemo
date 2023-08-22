phttps://github.com/meshramaravind/Online-Flora-Go-Go-App-UI-Androidackage com.app.splashscreen.model

import com.app.splashscreen.R

data class CustomModel(
    val image: Int,
    val description: String,
)

object SeriesList {

    fun getModel(): List<CustomModel> {


        val itemModel1 = CustomModel(
            R.drawable.ic_launcher_background,
            "Lord Of The Rings",
        )

        val itemModel2 = CustomModel(
            R.drawable.ic_launcher_foreground,
            "Harry Potter",
        )


        val itemModel3 = CustomModel(
            R.drawable.ic_launcher_background,
            "Interstellar",
        )


        val itemModel4 = CustomModel(
            R.drawable.ic_launcher_foreground,
            "Squid Game",
        )


        val itemModel5 = CustomModel(
            R.drawable.ic_launcher_background,
            "How I Met Your Mother",
        )


        val itemModel6 = CustomModel(
            R.drawable.ic_launcher_foreground,
            "Vikings",
        )

        val itemModel7 = CustomModel(
            R.drawable.ic_launcher_background,
            "The Walking Dead",
        )

        val itemModel8 = CustomModel(
            R.drawable.ic_launcher_foreground,
            "Spartacus",
        )
        val itemModel9 = CustomModel(
            R.drawable.ic_launcher_background,
            "Rick and Morty",
        )
        val itemModel10 = CustomModel(
            R.drawable.ic_launcher_foreground,
            "Hababam Sınıfı",
        )


        val itemList: ArrayList<CustomModel> = ArrayList()
        itemList.add(itemModel1)
        itemList.add(itemModel2)
        itemList.add(itemModel3)
        itemList.add(itemModel4)
        itemList.add(itemModel5)
        itemList.add(itemModel6)
        itemList.add(itemModel7)
        itemList.add(itemModel8)
        itemList.add(itemModel9)
        itemList.add(itemModel10)

        return itemList
    }

}

