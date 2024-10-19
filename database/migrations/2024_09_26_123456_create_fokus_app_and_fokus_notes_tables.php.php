<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateFokusAppAndFokusNotesTables extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        if (!Schema::hasTable('fokus_app')) {
            Schema::create('fokus_app', function (Blueprint $table) {
                $table->id();
                $table->string('username')->unique();
                $table->string('password');
                $table->string('email')->unique();
                $table->string('profile_picture')->nullable();
                $table->timestamps();
            });
        }

        if (!Schema::hasTable('fokus_notes')) {
            Schema::create('fokus_notes', function (Blueprint $table) {
                $table->id();
                $table->foreignId('fokus_app_id')->constrained('fokus_app')->onDelete('cascade');
                $table->string('title');
                $table->text('content');
                $table->timestamps();
            });
        }

        if (!Schema::hasTable('fokus_task')) {
            Schema::create('fokus_task', function (Blueprint $table) {
                $table->id();
                $table->foreignId('fokus_app_id')->constrained('fokus_app')->onDelete('cascade');
                $table->string('token')->unique();
                $table->string('task_title');
                $table->boolean('is_completed')->default(false); //task Field
                $table->timestamps();
            });
        }
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('fokus_task');
        Schema::dropIfExists('fokus_notes');
        Schema::dropIfExists('fokus_app');
    }
}
