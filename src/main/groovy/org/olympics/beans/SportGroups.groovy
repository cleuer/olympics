package org.olympics.beans

import org.springframework.stereotype.Component

/**
 * Broad sports group categories
 * Created by cjl7959 on 12/9/18.
 */

@Component
class SportGroups {
  static final Map<String, Integer> SPORT_GROUP = [
      'Ice Hockey' : 1,
      'Badminton' : 1,
      'Handball' : 1,
      'Water Polo' : 1,
      'Hockey' : 1,
      'Volleyball' : 1,
      'Beach Volleyball' : 1,
      'Synchronized Swimming' : 1,
      'Diving' : 2,
      'Swimming' : 2,
      'Speed Skating' : 2,
      'Sailing' : 2,
      'Gymnastics' : 3,
      'Boxing' : 3,
      'Wrestling' : 3,
      'Weightlifting' : 3,
      'Rhythmic Gymnastics' : 3,
      'Taekwondo' : 3,
      'Judo' : 3,
      'Cross Country Skiing' : 4,
      'Alpine Skiing' : 4,
      'Freestyle Skiing' : 4,
      'Alpinism' : 4,
      'Ski Jumping' : 4,
      'Short Track Speed Skating' : 5,
      'Figure Skating' : 5,
      'Table Tennis' : 5,
      'Athletics' : 5,
      'Archery' : 5,
      'Fencing' : 5,
      'Shooting' : 5,
      'Biathlon' : 5,
      'Luge' : 6,
      'Bobsleigh' : 6,
      'Skeleton' : 6,
      'Canoeing' : 6,
      'Equestrianism' : 6,
      'Cycling' : 6,
      'Rowing' : 6,
      'Nordic Combined' : 7,
      'Triathlon' : 7,
      'Modern Pentathlon' : 7,
      'Lacrosse' : 7,
      'Polo' : 7,
      'Cricket' : 7,
      'Curling' : 7,
      'Rugby' : 7,
      'Art Competitions' : 8,
      'Racquets' : 8,
      'Croquet' : 8,
      'Roque' : 8,
      'Motorboating' : 8,
      'Military Ski Patrol' : 8,
      'Basque Pelota' : 8,
      'Aeronautics' : 8,
      'Rugby Sevens' : 8,
      'Trampolining' : 8,
      'Tug-Of-War' : 8,
      'Jeu De Paume' : 8,
      'Baseball' : 9,
      'Softball' : 9,
      'Basketball' : 9,
      'Football' : 9,
      'Golf' : 10,
      'Tennis' : 10,
      'Snowboarding' : 10,

  ].asImmutable()

  Integer getGroup(String sport) {
    SPORT_GROUP.get(sport)
  }
}


