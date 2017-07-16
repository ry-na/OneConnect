<?php

namespace App\Http\Controllers\Api\V1;

use App\Models\Opinion;
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

    /**
     * @see http://qiita.com/qwe001/items/de4cf622cc22e49b9ada
     * @param Request $request
     * @return mixed
     */
    public function register(Request $request)
    {
        $validator = Validator::make(
            $request->all(),
            [
                Opinion::USER_ID => 'required',        // TODO: #11完了後、ユーザーが存在するするかをチェックする
                Opinion::OPINION_MESSAGE => 'required|max:1000',  // TODO: 文字数はUI見て最大数を決める
                Opinion::LAT => ['required', 'numeric', 'regex:/^[-]?((([0-8]?[0-9])(\.[0-9]{6}))|90(\.0{6})?)$/'],
                Opinion::LON => ['required', 'numeric', 'regex:/^[-]?(((([1][0-7][0-9])|([0-9]?[0-9]))(\.[0-9]{6}))|180(\.0{6})?)$/']
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
        $opinion{Opinion::USER_ID} = $request[Opinion::USER_ID];
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
}