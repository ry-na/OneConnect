<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;


/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class Reply extends Authenticatable
{
    const TABLE = 'replies';
    /**
     * REPLY_ID
     */
    const REPLY_ID = 'reply_id';
    /**
     * OPINION_ID
     */
    const OPINION_ID = 'opinion_id';
    /**
     * USER_ID
     */
    const USER_ID = 'user_id';
    /**
     * REPLY_MESSAGE
     */
    const REPLY_MESSAGE = 'reply_message';

    /**
     * @see https://readouble.com/laravel/5.3/ja/eloquent.html
     */
    protected $guarded = [
        self::REPLY_ID
    ];

    /**
     * Column to be obtained by Select statement.
     * @var array
     */
    public static $gettableColumns = [
        self::OPINION_ID,
        self::USER_ID,
        self::REPLY_MESSAGE,
        "created_at",
    ];
}
