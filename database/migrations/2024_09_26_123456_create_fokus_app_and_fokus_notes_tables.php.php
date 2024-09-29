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
        // Create the fokus_app table
        Schema::create('fokus_app', function (Blueprint $table) {
            $table->id();
            $table->string('username')->unique();
            $table->string('password');
            $table->string('email')->unique();
            $table->timestamps();
        });

        // Create the fokus_notes table
        Schema::create('fokus_notes', function (Blueprint $table) {
            $table->id();
            $table->string('title');
            $table->text('content');
            $table->foreignId('fokus_app_id')->constrained('fokus_app')->onDelete('cascade'); // Foreign key reference
            $table->timestamps(); // Ensure you have timestamps for notes as well
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('fokus_notes'); // Drop notes table first
        Schema::dropIfExists('fokus_app'); // Then drop fokus_app table
    }
}
