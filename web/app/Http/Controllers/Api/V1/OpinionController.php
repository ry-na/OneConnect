<?php

namespace App\Http\Controllers\Api\V1;

use App\Models\Opinion;
use App\Models\Reply;
use Illuminate\Http\Request;

/**
 *
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class OpinionController extends V1Controller
{
    const PREFIX = 'opinion';

    /**
     *
     * @return mixed
     */
    public function get()
    {
        return $this->json(
            200,
            Opinion::get(Opinion::$gettableColumns)
        );
    }

    /**
     *
     * @param Request $request
     * @return mixed
     */
    public function reply(Request $request)
    {
        return $this->json(
            200,
            Reply::where(Reply::OPINION_ID, $request->{Reply::OPINION_ID})
                ->get(Reply::$gettableColumns)
        );
    }
}