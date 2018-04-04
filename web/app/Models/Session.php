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
     * TIMEOUT
     */
    const TIMEOUT = 360000;
    /**
     * @see https://readouble.com/laravel/5.3/ja/eloquent.html
     */
    protected $guarded = [self::ID];

    /**
     * NOT_FOUND
     */
    const NOT_FOUND = "not_found";

    /**
     * OK
     */
    const OK = "ok";
    /**
     * Column to be obtained by Select statement.
     * @var array
     */
    public static $gettableColumns = [self::SESSION_ID, self::USER_ID, self::IP];

    public static function errorCodePublic($error)
    {
        return ($error >> 4) << 4;
    }

    public static function SID2ID($request)
    {
        $sid = $request->header(static ::SESSION_ID);
        $col = Session::where(static ::SESSION_ID, $sid)->get()->first();
        if (!$col) {
            return "";
        }
        return $col->{Session::USER_ID};
    }

    public static function auth($request)
    {
        $sid = $request->header(static ::SESSION_ID);
        $col = Session::where(static ::SESSION_ID, $sid)->get()->first();
        if (!$col) {
            return static::NOT_FOUND;
        }
        //CHECK1:TIME
        $time = $col->{Session::CREATED_AT}->timestamp;
        if (time() - $time > static ::TIMEOUT) {
            return "timeout";
        }
        return static::OK;
    }

    public static function register($user, $ip)
    {
        $sid = bin2hex(random_bytes(32)); //256bit
        static::create([
            static::SESSION_ID => $sid,
            static::USER_ID => $user->{User::EMAIL},
            static::IP => $ip
        ])->save();
        return $sid;
    }
}
