<?php

/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
$factory->define(\App\Models\User::class, function (Faker\Generator $faker) {
    return [
        \App\Models\User::PASS => hash(config('app.hash'),'sample')
    ];
});
