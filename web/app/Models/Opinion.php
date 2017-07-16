<?php

namespace App\Models;


/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class Opinion extends BaseModel
{
    const TABLE = 'opinions';

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
        self::ID
    ];

    /**
     * Column to be obtained by Select statement.
     * @var array
     */
    public static $gettableColumns = [
        self::ID,
        self::USER_ID,
        self::EVENT_ID,
        self::OPINION_MESSAGE,
        self::LAT,
        self::LON,
        self::CREATED_AT
    ];

    /**
     * @param $request
     */
    public function register(array $request)
    {
        static::create([
            static::USER_ID => $request[static::USER_ID],
            static::OPINION_MESSAGE => $request[static::OPINION_MESSAGE],
            static::LAT => $request[static::LAT],
            static::LON => $request[static::LON],
        ])->save();
    }
}
