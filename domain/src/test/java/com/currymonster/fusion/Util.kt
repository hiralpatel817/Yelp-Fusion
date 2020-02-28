package com.currymonster.fusion

import com.currymonster.fusion.data.*

class Util {

    companion object {
        fun randomBusinessGenerator(): Business {
            return Business(
                id = randomStringGenerator(),
                alias = randomStringGenerator(),
                name = randomStringGenerator(),
                imageUrl = randomStringGenerator(),
                isClosed = true,
                url = randomStringGenerator(),
                reviewCount = 40,
                categories = emptyList(),
                rating = 3.2,
                coordinates = Coordinates(1.0, 1.0),
                transactions = emptyList(),
                location = Location(
                    address = randomStringGenerator(),
                    displayAddress = randomStringGenerator(),
                    city = randomStringGenerator(),
                    state = randomStringGenerator(),
                    zip = randomStringGenerator()
                ),
                phone = randomStringGenerator(),
                displayPhone = randomStringGenerator(),
                distance = 23.3
            )
        }

        fun randomReviewGenerator(): Review {
            return Review(
                id = randomStringGenerator(),
                url = randomStringGenerator(),
                text = randomStringGenerator(),
                rating = 4,
                created = randomStringGenerator(),
                user = randomUserGenerator()
            )
        }

        private fun randomUserGenerator(): User {
            return User(
                id = randomStringGenerator(),
                profileUrl = randomStringGenerator(),
                imageUrl = randomStringGenerator(),
                name = randomStringGenerator()
            )
        }

        private fun randomStringGenerator(): String {
            val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

            return (1..10)
                .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");
        }
    }
}