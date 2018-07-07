package com.movie.nari.movieapp.model

data class NowPlaying(var vote_count: Long,
                      var id: Long,
                      var video: Boolean,
                      var vote_average: Double,
                      var title: String,
                      var popularity: Double,
                      var poster_path: String,
                      var original_language: String,
                      var original_title: String,
                      var genre_ids: List<Long>,
                      var backdrop_path: String,
                      var adult: Boolean,
                      var overview: String,
                      var release_date: String)