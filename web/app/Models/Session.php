<?php

namespace App\Models;


/**
 * @author Takahiro Otani <taka.oot@gmail.com>
 */
class Session extends BaseModel
{
    const TABLE = 'sessions';

    /**
     * OPINION_ID
     */
    const SESSION_ID = 'sid';
    /**
     * USER_ID
     */
    const USER_ID = 'user_id';
    /**
     * time
     */
    const TIME = 'time';
    /**
     * IP
     */
    const IP = 'ip';

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
        self::SESSION_ID,
        self::USER_ID,
        self::TIME,
        self::IP
    ];

       public static function register(array $session)
    {
           $sid=bin2hex(random_bytes(256));
        static::create([
            static::SESSION_ID => $request[static::EMAIL],
            static::USER_ID => $session[static::USER_ID],
            static::TIME => time(),
            static::IP => $session[static::IP]
        ])->save();
           return $sid;
    }
}
