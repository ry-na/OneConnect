<?php

namespace App\Http\Controllers\Api\V1;

use App\Models\Opinion;
use App\Models\Participant;
use App\Models\Session;
use App\Models\Reply;
use Illuminate\Http\Request;
use Validator;

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
     *
     */
    public function get()
    {
        return $this->json(
            200,
            Opinion::get(Opinion::$gettableColumns)
        );
    }

    /**
     * @param Request $request
     *
     * @return mixed
     */
    public function getReply(Request $request)
    {
        $validator = Validator::make(
            $request->all(),
            [
                Reply::OPINION_ID => 'required'
            ]
        );
        if ($validator->fails()) {
            return $this->json(
                400,
                [
                    static::ERROR => $validator->errors()->all()
                ]
            );
        }

        return $this->json(
            200,
            Reply::where('opinion_id', $request[Reply::OPINION_ID])->get(Reply::$gettableColumns)
        );
    }

    /**
     * @see http://qiita.com/qwe001/items/de4cf622cc22e49b9ada
     *
     * @param Request $request
     *
     * @return mixed
     */
    public function newOpinion(Request $request)
    {
        $validator = Validator::make(
            $request->all(),
            [
                Opinion::OPINION_MESSAGE => 'required|max:1000',  // TODO: 文字数はUI見て最大数を決める
                Opinion::LAT => [
                    'required',
                    'numeric',
                    'regex:/^[-]?((([0-8]?[0-9])(\.[0-9]{6}))|90(\.0{6})?)$/'
                ],
                Opinion::LON => [
                    'required',
                    'numeric',
                    'regex:/^[-]?(((([1][0-7][0-9])|([0-9]?[0-9]))(\.[0-9]{6}))|180(\.0{6})?)$/'
                ]
            ]
        );
        if ($validator->fails()) {
            return $this->json(
                400,
                [
                    static::ERROR => $validator->errors()->all()
                ]
            );
        }

        $opinion = new Opinion();
        $opinion{Opinion::USER_ID} = Session::sessionIdToUserId($request);
        $opinion{Opinion::OPINION_MESSAGE} = $request[Opinion::OPINION_MESSAGE];
        $opinion{Opinion::LAT} = $request[Opinion::LAT];
        $opinion{Opinion::LON} = $request[Opinion::LON];
        if (!$opinion->save()) {
            return $this->json(
                400,
                [
                    static::ERROR => "Insert失敗"
                ]
            );
        }

        return $this->json(
            200,
            [
                Opinion::ID => $opinion->{Opinion::ID},
                Opinion::CREATED_AT => $opinion->{Opinion::CREATED_AT}
            ]
        );
    }

    /**
     *
     * @param Request $request
     *
     * @return mixed
     */
    public function reply(Request $request)
    {
        $validator = Validator::make(
            $request->all(),
            [
                Reply::OPINION_ID => 'required',
                Reply::REPLY_MESSAGE => 'required|max:1000'  // TODO: 文字数はUI見て最大数を決める
            ]
        );
        if ($validator->fails()) {
            return $this->json(
                400,
                [
                    static::ERROR => $validator->errors()->all()
                ]
            );
        }
        $reply = new Reply();
        $reply{Reply::USER_ID} = Session::sessionIdToUserId($request);
        $reply{Reply::OPINION_ID} = $request[Reply::OPINION_ID];
        $reply{Reply::REPLY_MESSAGE} = $request[Reply::REPLY_MESSAGE];
        if (!$reply->save()) {
            return $this->json(
                400,
                [
                    static::ERROR => "Insert失敗"
                ]
            );
        }

        return $this->json(
            200,
            [
                Reply::CREATED_AT => $reply->{Reply::CREATED_AT}
            ]
        );
    }

    /**
     *
     * @param Request $request
     *
     * @return mixed
     */
    public function participant(Request $request)
    {
        $validator = Validator::make(
            $request->all(),
            [
                Participant::OPINION_ID => 'required',
            ]
        );
        if ($validator->fails()) {
            return $this->json(
                400,
                [
                    static::ERROR => $validator->errors()->all()
                ]
            );
        }
        $participant = new Participant();
        $participant{Participant::USER_ID} = Session::sessionIdToUserId($request);
        $participant{Participant::OPINION_ID} = $request[Participant::OPINION_ID];
        if (!$participant->save()) {
            return $this->json(
                400,
                [
                    static::ERROR => "Insert失敗"
                ]
            );
        }

        return $this->json(
            200,
            [
                $participant
            ]
        );
    }

    /**
     *
     * @param Request $request
     *
     * @return mixed
     */
    public function isParticipant(Request $request)
    {
        $validator = Validator::make(
            $request->all(),
            [
                Participant::OPINION_ID => 'required',
            ]
        );
        if ($validator->fails()) {
            return $this->json(
                400,
                [
                    static::ERROR => $validator->errors()->all()
                ]
            );
        }
        $participant = Participant::where(Participant::USER_ID, Session::sessionIdToUserId($request))
            ->where(Participant::OPINION_ID, $request[Participant::OPINION_ID])
            ->first();
        if ($participant == null) {
            return $this->json(
                200,
                [
                    Participant::IS_PARTICIPANT => false
                ]
            );
        }

        return $this->json(
            200,
            [
                Participant::IS_PARTICIPANT => true
            ]
        );
    }

    public function complete($id, Request $request)
    {
        $opinion = Opinion::where('id', $id)
            ->where('user_id', Session::sessionIdToUserId($request))
            ->first();
        if (!$opinion) {
            return $this->json(
                400,
                [
                    static::ERROR => 'Your opinion is nof found'
                ]
            );
        }
        $opinion->is_completed = true;
        if (!$opinion->save()) {
            return $this->json(
                400,
                [
                    static::ERROR => 'Your opinion cannot complete'
                ]
            );
        }
        return $this->json(
            200,
            [
                "updated_at" => $opinion->updated_at
            ]
        );
    }
}
