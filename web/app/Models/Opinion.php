<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;


/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class Opinion extends Authenticatable
{
    const TABLE = 'opinions';
    /**
     * OPINION_ID
     */
    const OPINION_ID = 'opinion_id';
    /**
     * USER_ID
     */
    const USER_ID = 'user_id';
    /**
     * EVENT_ID
     */
    const EVENT_ID = 'event_id';
    /**
     * OPINION_MESSAGE
     */
    const OPINION_MESSAGE = 'opinion_message';
    /**
     * LAT
     */
    const LAT = 'lat';
    /**
     * LON
     */
    const LON = 'lon';

    /**
     * @see https://readouble.com/laravel/5.3/ja/eloquent.html
     */
    protected $guarded = [
        self::OPINION_ID
    ];

    /**
     * Column to be obtained by Select statement.
     * @var array
     */
    public static $gettableColumns = [
        self::OPINION_ID,
        self::USER_ID,
        self::EVENT_ID,
        self::OPINION_MESSAGE,
        self::LAT,
        self::LON,
        "created_at",
    ];
}
