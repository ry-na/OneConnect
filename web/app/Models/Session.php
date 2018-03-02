<?php

namespace App\Models;

use App\Models\User;
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
        self::IP
    ];

       public static function register($user,$ip)
    {
           $sid=bin2hex(random_bytes(32)); //256bit
        static::create([
            static::SESSION_ID => $sid,
            static::USER_ID => $user->{User::EMAIL},
            static::IP => $ip
        ])->save();
           return $sid;
    }
}
