package org.olympics.domains

/**
 * Season of Olympic game: Winter or Summer
 * Created by C. Leuer
 */
enum Season {

  Winter('Winter'), Summer ('Summer')

  String value

  Season(String value) {
    this.value = value
  }

}