<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class NotesModel extends Model
{
    use HasFactory;

    protected $fillable = [
        'title',
        'content',
        'fokus_app_id' // associates the note with a user
    ];

    
        protected $table = 'fokus_notes';  // table name

    // relationship back to the FokusApp model
    public function fokusApp()
    {
        return $this->belongsTo(FokusApp::class, 'fokus_app_id'); // foreign key
    }
    
}
