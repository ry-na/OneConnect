<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Participant extends Model
{
    /**
     * USER_ID
     */
    const USER_ID = 'user_id';
    /**
     * OPINION_ID
     */
    const OPINION_ID = 'opinion_id';
    /**
     * CREATED_AT
     */
    const CREATED_AT = 'created_at';
    /**
     * IS_PARTICIPANT
     */
    const IS_PARTICIPANT = 'is_participant';


    public static $gettableColumns = [
        self::CREATED_AT
    ];
}
