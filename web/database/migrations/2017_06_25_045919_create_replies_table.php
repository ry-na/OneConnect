<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRepliesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create(\App\Models\Reply::TABLE, function (Blueprint $table) {
            $table->increments(\App\Models\Reply::REPLY_ID);
            $table->timestamps();
            $table->softDeletes();
            $table->string(\App\Models\Reply::OPINION_ID);
            $table->string(\App\Models\Reply::USER_ID);
            $table->string(\App\Models\Reply::REPLY_MESSAGE);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists(\App\Models\Reply::TABLE);
    }
}
