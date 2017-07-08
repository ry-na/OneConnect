<?php

/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
$factory->define(\App\Models\Opinion::class, function (Faker\Generator $faker) {
    return [
        \App\Models\Opinion::USER_ID => 1,
        \App\Models\Opinion::EVENT_ID => null,
        \App\Models\Opinion::OPINION_MESSAGE => "",
        \App\Models\Opinion::LAT => 35.689634,
        \App\Models\Opinion::LON => 139.692100
    ];
});
