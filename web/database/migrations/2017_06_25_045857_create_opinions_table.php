<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateOpinionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create(\App\Models\Opinion::TABLE, function (Blueprint $table) {
            $table->increments(\App\Models\Opinion::ID);
            $table->timestamps();
            $table->softDeletes();
            $table->string(\App\Models\Opinion::USER_ID);
            $table->string(\App\Models\Opinion::EVENT_ID)->nullable();
            $table->string(\App\Models\Opinion::OPINION_MESSAGE);
            $table->double(\App\Models\Opinion::LAT);
            $table->double(\App\Models\Opinion::LON);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists(\App\Models\Opinion::TABLE);
    }
}
