package org.olympics.domains

/**
 * Season of Olympic game: Winter or Summer
 * Created by cjl7959 on 12/6/18.
 */
enum Season {

  Winter('Winter'), Summer ('Summer')

  String value

  Season(String value) {
    this.value = value
  }

}