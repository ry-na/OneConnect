<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;


/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class User extends Authenticatable
{
    const TABLE = 'users';
    /**
     * ID
     */
    const ID = 'id';
    /**
     * EMAIL
     */
    const EMAIL = 'email';
    /**
     * PASS
     */
    const PASS = 'pass';
    /**
     * SESSION_ID
     */
    const SESSION_ID = 'sid';

    /**
     * @see https://readouble.com/laravel/5.3/ja/eloquent.html
     */
    protected $fillable = [
        self::EMAIL,
        self::PASS
    ];

    /**
     * @param $request
     */
    public static function registerUser(array $request)
    {
        self::create([
            self::EMAIL => $request[static::EMAIL],
            self::PASS => $request[static::PASS],
        ])->save();
    }

    /**
     * @return string
     */
    public function registerSId()
    {
        $token = sha1(uniqid(rand(), true));
        $this->{User::SESSION_ID} = $token;
        $this->save();
        return $token;
    }
}
