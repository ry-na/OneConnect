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
     * RESULT
     */
    const AUTH_SUCCESS=0x0000;
    const AUTH_ERROR_TIMEOUT=0x1000;
    const AUTH_ERROR_NOTFOUND=0x1001;
      const AUTH_ERROR_INVALIDIP=0x1002;
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
public static function ErrorCode_Public($error){
return ($error >> 4) << 4;
}
public static function Auth($request) {
  $sid = $request->header(static::SESSION_ID);
 $col = Session::where(static::SESSION_ID, $sid)->get()->first();
if(!$col)return static::AUTH_ERROR_NOTFOUND;
//CHECK1:TIME
$time=$col->{Session::CREATED_AT}->timestamp;
 if(time()-$time>static::TIMEOUT)return static::AUTH_ERROR_TIMEOUT;
//CHECK2:IP
$ip=$col->{Session::IP};
if($col!==$ip)return static::AUTH_ERROR_INVALIDIP;
return static::AUTH_SUCCESS;
}
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
