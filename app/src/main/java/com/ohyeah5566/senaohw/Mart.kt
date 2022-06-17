package com.ohyeah5566.senaohw

data class Mart(
    val martId: String = "",
    val martName: String = "",
    val martShortName: String = "",
    val price: Int = 0,
    val finalPrice: Int = 0,
    val imageUrl: String = "",
    val stockAvailable: Int = 0
)


/*
{
            "price": 39950,
            "martShortName": "iPhone 12 Pro Max 256GB",
            "imageUrl": "https://pdinfo.senao.com.tw/octopus/contents/99b404a6bcfb4a74a27e4a10746fb258.jpg",
            "finalPrice": 39950,
            "martName": "iPhone 12 Pro Max 256GB【下殺97折 送保護貼兌換券】",
            "stockAvailable": 30,
            "martId": 1250797
        }
 */