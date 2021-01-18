package com.mvvm.project.Utils

public class Constants{

    companion object{
        public val BASE_URL = "https://recipesapi.herokuapp.com/api/"
        public val API_KEY = "";

        public val  CONNECTION_TIMEOUT = 10;
        public val  READ_TIMEOUT = 2;
        public var  WRITE_TIMEOUT =2;

        public var  REFRESH_TIME = 60 * 60 *24 *30;


        val DEFAULT_SEARCH_CATEGORIES = arrayOf(
            "Barbeque",
            "Breakfast",
            "Chicken",
            "Beef",
            "Brunch",
            "Dinner",
            "Wine",
            "Italian"
        )

        val DEFAULT_SEARCH_CATEGORY_IMAGES = arrayOf(
            "barbeque",
            "breakfast",
            "chicken",
            "beef",
            "brunch",
            "dinner",
            "wine",
            "italian"
        )
    }

}